#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

input string UserInputs = "-------------------------------------";
// Any user's inputs.

string advisorUUID = NULL;

int OnInit()
  {
   InputsProvider().InputLong("magic", MagicNumber);
   InputsProvider().InputString("strategyName", StrategyName);
   InputsProvider().InputDatetime("startTime", TimeCurrent());
   // Add inputs like example.
   string inputs = InputsProvider().Build();
   advisorUUID = ApiService().Connect(inputs);
   if(advisorUUID == NULL)
      {
       Alert("Connection fail. StrategyName not found. StrategyName should be equals to Java server side interface implementation Strategy.getName()");
       return (INIT_FAILED);
      }
   else
      Alert("Connection success. Advisor UUID: " + advisorUUID);
   return(INIT_SUCCEEDED);
  }

void OnDeinit(const int reason) { DestroyContext(); }

void OnTick()
  {
   Signal signals[];
   ApiService().GetSignals(signals);
   SignalExecutor().Execute(signals);
  }
