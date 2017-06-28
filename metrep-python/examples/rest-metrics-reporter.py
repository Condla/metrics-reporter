```
Example for RESTMetricsReporter
```


if __name__ == "__main__":
    from pprint import pprint
    URL_BASE = "http://devbox.t-mobile.at:6188"
    METRIC = "mymetric"
    APPID = "my_app_0815"
    HOSTNAME = "servicehost.example.com"
    SOURCE_URL = "http://{}/v1/api/metrics".format(HOSTNAME)
    JSON_KEY = "mymetric"
    ms = RESTMetricsReporter(URL_BASE, SOURCE_URL, JSON_KEY)
    ms.set_metricname(METRIC)
    ms.set_appid(APPID)
    ms.set_hostname(HOSTNAME)
    response = ms.post()
    pprint(response)
