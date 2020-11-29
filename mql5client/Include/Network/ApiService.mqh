#property strict

#include <Libs\JAson.mqh>
#include <Common\Structures.mqh>
#include <Network\RestClient.mqh>

//+------------------------------------------------------------------+
//| Service for call local or remote resources                       |
//+------------------------------------------------------------------+
class ApiService
  {
   RestClient *      _restClient;
   CJAVal *          _jsonParser;
   string            _strategy;
   string            _advisor_id;
   string            _symbol;
public:
                     ApiService(long magic, string symbol, string strategy)
     {
      RestConfig config = { "http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };
      _restClient = new RestClient(magic, symbol, config);
      _jsonParser = new CJAVal(NULL, jtUNDEF);
      _strategy = strategy;
      _symbol = symbol;
     }
                    ~ApiService() { delete _restClient; delete _jsonParser; }
public:
   string             Connect(string inputs);
   void                 GetSignals(Signal &signal);
  };
//+------------------------------------------------------------------+
//| Connect MT advisor with server side. Return advisor's UUID       |
//+------------------------------------------------------------------+
string::ApiService Connect(string inputs)
  {
   string responceBody = _restClient.Connect(inputs);
   _jsonParser.Clear();
   _jsonParser.Deserialize(responceBody);
   return _advisor_id = _jsonParser["id"].ToStr();
  }
//+------------------------------------------------------------------+
//| Get signal as strategy result                                    |
//+------------------------------------------------------------------+
void::ApiService GetSignals(Signal &signals[])
  {
   string responceBody = _restClient.GetSignals(_advisor_id, _strategy);
   _jsonParser.Clear();
   _jsonParser.Deserialize(responceBody);
   for(int i = 0; i < _jsonParser.Size(); i++)
     {
      Signal signal;
      signal.lot = _jsonParser[i]["lot"].ToDbl();
      signal.advisorId = _jsonParser[i]["id"].ToStr();
      signal.positionId = _jsonParser[i]["positionId"].ToInt();
      signal.type = (SignalType) _jsonParser[i]["type"].ToStr();
      signal.stopLoss = (int) _jsonParser[i]["stopLoss"].ToInt();
      signal.takeProfit = (int) _jsonParser[i]["takeProfit"].ToInt();
      int size = ArraySize(signals);
      ArrayResize(signals, size + 1);
      signals[size] = signal;
     }
  }
//+------------------------------------------------------------------+