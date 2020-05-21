import pandas as pd 
import sqlalchemy
from google.cloud import secretmanager
import os 

def query_states_daily():
    states_url = 'https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-states.csv'
    statesdf = pd.read_csv(states_url)
    statesdf['date'] = pd.to_datetime(statesdf['date'])
    # shift the date 
    statesdf['shift_date'] = statesdf['date'] + pd.DateOffset(days=1)
    # grab only if the cases & deaths greater than 0
    subquery1df = statesdf[statesdf.cases + statesdf.deaths > 0][['state','fips','cases','shift_date']]
    # merge the two on shift_date and regular date 
    merged = statesdf.merge(subquery1df,left_on=['date','fips','state'],right_on=['shift_date','fips','state'],how='inner')
    print(merged.head(10))
    merged['new_cases'] = merged['cases_x'] - merged['cases_y']
    result = merged[['date','state','new_cases']].sort_values(by=['state','date'],ascending=[True,True])
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
    df.to_sql('temp_table', engine, if_exists='replace', dtype={'state':sqlalchemy.types.VARCHAR(length=255)})    
    sql = """
    INSERT INTO states_table
    SELECT date, state, new_cases FROM temp_table
    ON DUPLICATE KEY UPDATE states_table.new_cases = temp_table.new_cases
    """
    with engine.begin() as conn:
        conn.execute(sql)
    
def main(request):
    df = query_states_daily()
    insertDF(df)