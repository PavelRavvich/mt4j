#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <JAson.mqh>
#include <RestClient.mqh>
#include <Structures.mqh>


class ApiService
  {

   RestClient *      _restClient;

   CJAVal *          _jsonParser;

   string            _strategy;

   string            _advisor_id;

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
      return _advisor_id = _jsonParser["id"].ToStr();
     }

   void                 GetSignal(Signal &signal)
     {
      string responceBody = _restClient.GetSignal(_advisor_id, _strategy, Symbol());
      _jsonParser.Clear();
      _jsonParser.Deserialize(responceBody);
      signal.lot = _jsonParser["lot"].ToDbl();
      signal.type = _jsonParser["type"].ToStr();
      signal.advisorId = _jsonParser["id"].ToStr();
      signal.stopLoss = _jsonParser["stopLoss"].ToInt();
      signal.takeProfit =  _jsonParser["takeProfit"].ToInt();
      signal.positionId = _jsonParser["positionId"].ToLong();
     }

};