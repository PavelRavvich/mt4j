#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Common\Structures.mqh>
#include <Network\RestClient.mqh>

//+------------------------------------------------------------------+
//| Service for call local or remote resources                       |
//+------------------------------------------------------------------+
class ApiService
  {
   RestClient *      _restClient;
   CJAVal *          json_mapper;
   string            _strategy;
   string            _advisor_id;
   string            _symbol;
public:
                     ApiService(long magic, string symbol, string strategy)
     {
      RestConfig config = { "http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };
      _restClient = new RestClient(magic, symbol, config);
      json_mapper = new CJAVal(NULL, jtUNDEF);
      _strategy = strategy;
      _symbol = symbol;
     }
                    ~ApiService() { delete _restClient; }
public:
   string            Connect(string inputs);
   void              GetSignals(Signal &signal);
  };
//+------------------------------------------------------------------+
//| Connect MT advisor with server side. Return advisor's UUID       |
//+------------------------------------------------------------------+
string::ApiService Connect(string inputs)
  {
   string responceBody = _restClient.Connect(inputs);
   json_mapper.Clear();
   json_mapper.Deserialize(responceBody);
   return _advisor_id = json_mapper["id"].ToStr();
  }
//+------------------------------------------------------------------+
//| Get signal as strategy result                                    |
//+------------------------------------------------------------------+
void::ApiService GetSignals(Signal &signals[])
  {
   string responceBody = _restClient.GetSignals(_advisor_id, _strategy);
   json_mapper.Clear();
   json_mapper.Deserialize(responceBody);
   for(int i = 0; i < json_mapper.Size(); i++)
     {
      Signal signal;
      signal.lot = json_mapper[i]["lot"].ToDbl();
      signal.advisorId = json_mapper[i]["id"].ToStr();
      signal.positionId = json_mapper[i]["positionId"].ToInt();
      signal.type = (SignalType) json_mapper[i]["type"].ToStr();
      signal.stopLoss = (int) json_mapper[i]["stopLoss"].ToInt();
      signal.takeProfit = (int) json_mapper[i]["takeProfit"].ToInt();
      int size = ArraySize(signals);
      ArrayResize(signals, size + 1);
      signals[size] = signal;
     }
  }
//+------------------------------------------------------------------+