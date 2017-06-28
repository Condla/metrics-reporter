import sys
from resource_management import *


class TestClient(Script):

  def install(self, env):
    print 'Install the client'

  def configure(self, env):
    print 'Configure the client'


if __name__ == "__main__":
  TestClient().execute()
