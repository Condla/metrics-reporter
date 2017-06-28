'''
Example of how to write your own MetricsReporter and use it
'''


import random
from pprint import pprint
from metrep import GenericMetricsReporter

def generate_value():
    '''
    Generate a random standard normal distributed value
    '''
    return random.gauss(0, 0.5)


if __name__ == "__main__":
    URL_BASE = "http://localhost:6188"
    METRIC = "mygenericmetric"
    APPID = "mydummyapp"
    HOSTNAME = "devbox.t-mobile.at"
    ms = GenericMetricsReporter(URL_BASE, METRIC, APPID, generate_value)
    ms.set_hostname(HOSTNAME)
    response = ms.post()
    pprint(response)
