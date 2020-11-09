#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <JAson.mqh>
#include <RestClient.mqh>

class ApiService
  {

   RestClient *      _restClient;

   CJAVal *          _jsonParser;

public:
                     ApiService(long magic)
     {
      RestConfig config = { "http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };
      _restClient = new RestClient(magic, config);
      _jsonParser = new CJAVal(NULL, jtUNDEF);
     }
                    ~ApiService() { delete _restClient; delete _jsonParser; }

public:

   string             Connect(string inputs)
     {
      string responceBody = _restClient.Connect(inputs);
      _jsonParser.Clear();
      _jsonParser.Deserialize(responceBody);
      return _jsonParser["id"].ToStr();
     }

};