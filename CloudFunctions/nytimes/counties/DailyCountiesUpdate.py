import pandas as pd 
import sqlalchemy
from google.cloud import secretmanager
import os 

def query_counties_daily():
    counties_url = 'https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-counties.csv'
    countiesdf = pd.read_csv(counties_url)
    countiesdf['date'] = pd.to_datetime(countiesdf['date'])
    # shift the date 
    countiesdf['shift_date'] = countiesdf['date'] + pd.DateOffset(days=1)
    # grab only if the cases greater than 0
    subquery1df = countiesdf[countiesdf.cases > 0][['county','state','fips','cases','shift_date']]
    # merge the two on shift_date and regular date 
    merged = countiesdf.merge(subquery1df,left_on=['date','fips','county','state'],right_on=['shift_date','fips','county','state'],how='inner')
    merged['new_cases'] = merged['cases_x'] - merged['cases_y']
    result = merged[['date','county','state','new_cases']].sort_values(by=['state','date'],ascending=[True,True])
    return result

def insertDF(df):
    client = secretmanager.SecretManagerServiceClient()
    project_number = os.environ['project_number']
    db_user = os.environ['db_user']
    connection_name = os.environ['connection_name']
    password_link = f"projects/{project_number}/secrets/db_password/versions/latest"
    response = client.access_secret_version(password_link)
    db_password = response.payload.data.decode('UTF-8')
    db_name = 'nytimes'
    engine = sqlalchemy.create_engine(f"mysql+pymysql://{db_user}:{db_password}@/{db_name}?unix_socket=/cloudsql/{connection_name}")
    df.to_sql('county_temp_table', engine, if_exists='replace', dtype={'state':sqlalchemy.types.VARCHAR(length=255), 'county':sqlalchemy.types.VARCHAR(length=255)})
    sql = """
    INSERT INTO county_table
    SELECT state,date, county, new_cases FROM county_temp_table
    ON DUPLICATE KEY UPDATE county_table.new_cases = county_temp_table.new_cases
    """
    with engine.begin() as conn:
        conn.execute(sql)
    
def main(request):
    df = query_counties_daily()
    insertDF(df)