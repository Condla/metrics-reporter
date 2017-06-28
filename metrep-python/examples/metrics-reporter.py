'''
Example of how to write your own MetricsReporter and use it
'''

from metrep import MetricsReporter
import random
from pprint import pprint



def generate_value():
    '''
    Generate a random standard normal distributed value
    '''
    return random.gauss(0, 0.5)


class MyMetricsReporter(MetricsReporter):
    '''
    Implemenation of MetricsReporter
    '''

    PATH = "/ws/v1/timeline/metrics"

    def __init__(self, url_base):
        MetricsReporter.__init__(self, url_base)

    def calculate_metric(self):
        '''
        Use external function to generate a random value
        '''
        return generate_value()


if __name__ == "__main__":
    URL_BASE = "http://devbox.t-mobile.at:6188"
    METRIC = "myMetric"
    APPID = "my_app_0815"
    HOSTNAME = "devbox.t-mobile.at"
    ms = MyMetricsReporter(URL_BASE, METRIC, APPID)
    ms.set_hostname(HOSTNAME)
    response = ms.post()
    pprint(response)
