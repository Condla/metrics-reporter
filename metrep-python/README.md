# Metrics Reporter

Metrics Reporter is a Python tool, that connects to a AmbariMetricsCollector and
sends custom data to it.
You only need to create a Python class that inherits from MetricsReporter and
implements its calculate_metric method.

## Prerequisties
It must be possible to install the requests module in your environment.

## Install package with
pip install -e .

## Example Implemenation
You can find a simple example in the examples directory.

## RESTMetricsReporter
The RESTMetricsReporter is an implementation that obtains a value from a REST
interface and posts the value to the AmbariMetricsCollector.

### Usage Example:
Imagine you have a service running with a metrics port 8888 on host ```servicehost.example.com```
and you want to obtain the metric "mymetric".
The service exposes this metric as json response to the path ```/v1/api/metrics``` as
key "mymetric".
An Ambari metrics collector service is running on ```collector.example.com:6180```.

The script to realize this can be found in the examples directory.

## Oozie scheduling
These scripts are supposed to run once in a certain time frame.
They can be triggered by a Nifi job or via an Oozie shell action.


## TODOS:
* Add sample implementation of a Hive row count
* Add shell action with example on how to implement Hive row count.
* Document how to make a custom metric available from Ambari UI

## Directory Structure
metrep: Python metrics reporting tool
examples: Python metrics reporting tool usage examples
use_cases: Ambari Stacks for example and real use cases as well as a README.md on how to use/deploy it
