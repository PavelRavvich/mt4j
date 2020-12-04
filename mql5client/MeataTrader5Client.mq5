#property strict

#include <Common\Structures.mqh>
#include <ApplicationContext\ApplicationContext.mqh>

input string UserInputs = "-------------------------------------";
// Any user's inputs.

string advisorUUID = NULL;

int OnInit()
  {
   InputsProvider().InputInteger("Magic", Magic);
   InputsProvider().InputString("Strategy", Strategy);
   InputsProvider().InputInteger("StopLoss", StopLoss);
   InputsProvider().InputInteger("TakeProfit", TakeProfit);
   InputsProvider().InputDatetime("StartTime", TimeCurrent());
   // Add inputs like example.
   string inputs = InputsProvider().Build();
   advisorUUID = apiService().Connect(inputs);
   if(advisorUUID == NULL)
      return (INIT_FAILED);
   else
      Alert("Connection success. Advisor UUID: " + advisorUUID);
   return(INIT_SUCCEEDED);
  }

void OnDeinit(const int reason) { DestroyContext(); }

void OnTick()
  {
   Signal signals[];
   apiService().GetSignals(signals);
   signalExecutor().Execute(signals);
  }
