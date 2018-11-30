CREATE TABLE ETL_WORKFLOWHISTORY_S (
	completed_date VARCHAR2(16 BYTE), 
	complaint_id NUMBER(10,0), 
	activity_desc VARCHAR2(60 BYTE), 
	activity_id NUMBER(10,0), 
	stage_id NUMBER(10,0), 
	Stage_Desc VARCHAR2(40 BYTE), 
	ccrbreceived_date VARCHAR2(16 BYTE), 
	incident_date VARCHAR2(22 BYTE), 
	precinct VARCHAR2(40 BYTE), 
	boro VARCHAR2(40 BYTE), 
	casestatus VARCHAR2(6 BYTE), 
	ComplaintActivityOrder NUMBER(19,0)
);

GRANT INSERT, UPDATE, DELETE, SELECT, INDEX ON ETL_WORKFLOWHISTORY_S TO CCRBSL_ETL;