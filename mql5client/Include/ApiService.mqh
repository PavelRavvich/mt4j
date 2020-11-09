#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <JAson.mqh>
#include <RestClient.mqh>

class ApiService
  {

   RestClient *      _restClient;

   CJAVal *          _jsonParser;

   string            _strategy;

public:
                     ApiService(long magic, string strategy)
     {
      RestConfig config = { "http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };
      _restClient = new RestClient(magic, config);
      _jsonParser = new CJAVal(NULL, jtUNDEF);
      _strategy = strategy_name;
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

   string               GetSignal(string advisor_id)
     {
      string responceBody = _restClient.GetSignal(advisor_id, _strategy, Symbol());
      // todo impl
      return "";
     }

   string               AddPosition(Position &position)
     {
       string responceBody = _restClient.AddPosition(position);
       // todo impl
       return "";
     }

   string               UpdatePosition(Position &position)
     {
       string responceBody = _restClient.UpdatePosition(position);
       // todo impl
       return "";
     }

   string               HistoryPosition(Position &position)
     {
       string responceBody = _restClient.HistoryPosition(position);
       // todo impl
       return "";
     }

};