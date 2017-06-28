# Sample Application for Ambari Monitoring

## Ambari Stack

In the Ambari stack a monitoring "client" is defined. It defines the metrics and
widgets that Ambari is supposed to show.

## Deployment of the Ambari Stack

Deploy the UBERCOUNT directory into ```/var/lib/ambari-server/resources/stacks/HDP/2.0.6/services/```

The UBERCOUNT directory contains the following files and subdirectories
```
#TODO: tree UBERCOUNT
```

Don't forget to check if the following files are executable:
* metainfo.xml
* metrics.json
* widgets.json
* uber_client.py

### Prerequisites

* Monitoring user/principal needs to be created.
* Keytab needs to be created and accessible by ambari OS user.
* Give user access to resources, that you want to monitor.
* In this sample monitoring app


## Data

This is sample monitoring is build by using data from Uber:
https://github.com/fivethirtyeight/uber-tlc-foil-response/tree/master/uber-trip-data

Two data sets are chosen loaded manually into hdfs and manually into a Hive table.

The purpose of this is to have data available to do counts on.
The plan is to show the count metrics of both tables in one ambari widget.

### Commands for Repreducability
```
hdfs dfs -put uber-raw-data-apr14.csv /data/stage0
hdfs dfs -put uber-raw-data-aug14.csv /data/stage0
CREATE TABLE uberraw1 (datetime string, xcoord string, ycoord string, identifier string) row format delimited fields terminated by ',';
LOAD DATA INPATH '/data/stage0/uber-raw-data-apr14.csv' OVERWRITE INTO TABLE uberraw;
CREATE TABLE uberraw2 (datetime string, xcoord string, ycoord string, identifier string) row format delimited fields terminated by ',';
LOAD DATA INPATH '/data/stage0/uber-raw-data-aug14.csv' OVERWRITE INTO TABLE uberraw2;
select count(*) from uberraw;
select count(*) from uberraw2;
```
