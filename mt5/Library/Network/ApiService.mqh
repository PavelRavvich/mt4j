#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Service for call local or remote resources                       |
//+------------------------------------------------------------------+
class CApiService
  {
private:
   CRestClient *     rest_client;
   CJAVal *          json_mapper;
   string            strategy;
   string            advisor_id;
public:
                     CApiService(void);
                    ~CApiService(void) {}
public:
   string            Connect(string inputs);
   void              GetSignals(Signal &signals[]);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CApiService::CApiService(void)
  {
   rest_client = RestClient();
   json_mapper = JsonMapper();
  }
//+------------------------------------------------------------------+
//| Connect MT advisor with server side. Return advisor's UUID       |
//+------------------------------------------------------------------+
string CApiService::Connect(string inputs)
  {
   string responceBody = rest_client.Connect(inputs);
   json_mapper.Clear();
   json_mapper.Deserialize(responceBody);
   return advisor_id = json_mapper["id"].ToStr();
  }
//+------------------------------------------------------------------+
//| Get signal as strategy result                                    |
//+------------------------------------------------------------------+
void CApiService::GetSignals(Signal &signals[])
  {
   string responceBody = rest_client.GetSignals(advisor_id, Strategy());
   json_mapper.Clear();
   json_mapper.Deserialize(responceBody);
   for(int i = 0; i < json_mapper.Size(); i++)
     {
      Signal signal;
      signal.lot = json_mapper[i]["lot"].ToDbl();
      signal.type = json_mapper[i]["type"].ToStr();
      signal.advisorId = json_mapper[i]["id"].ToStr();
      signal.positionId = json_mapper[i]["positionId"].ToInt();
      signal.stopLoss = (int) json_mapper[i]["stopLoss"].ToInt();
      signal.takeProfit = (int) json_mapper[i]["takeProfit"].ToInt();
      int size = ArraySize(signals);
      ArrayResize(signals, size + 1);
      signals[size] = signal;
     }
  }
//+------------------------------------------------------------------+