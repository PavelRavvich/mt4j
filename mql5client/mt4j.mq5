#property strict

#include <Provider\InputsProvider.mqh>
#include <Transaction\SignalExecutor.mqh>
#include <Network\ApiService.mqh>
#include <Common\Structures.mqh>

input long Magic;
input string Strategy;
input string UserInputs = "-------------------------------------";
input int StopLoss = 100;
input int TakeProfit = 100;

string advisorUUID = NULL;

ApiService * apiService;
InputsProvider * inputsProvider;
SignalExecutor * signalExecutor;

int OnInit()
  {
   inputsProvider = new InputsProvider();
   apiService = new ApiService(Magic, Symbol(), Strategy);
   signalExecutor = new SignalExecutor(Magic, Symbol());
   advisorUUID = apiService.Connect(CollectInputs());
   if(advisorUUID == NULL)
      return (INIT_FAILED);
   else
      Alert("Connection success. Advisor UUID: " + advisorUUID);
   return(INIT_SUCCEEDED);
  }

void OnDeinit(const int reason) { delete apiService; delete inputsProvider; delete signalExecutor; }

string CollectInputs()
  {
   inputsProvider.InputInteger("Magic", Magic);
   inputsProvider.InputString("Strategy", Strategy);
   inputsProvider.InputInteger("StopLoss", StopLoss);
   inputsProvider.InputInteger("TakeProfit", TakeProfit);
   inputsProvider.InputDatetime("StartTime", TimeCurrent());
   // Add inputs like example.
   return inputsProvider.Build();
  }

void OnTick()
  {
   Signal signals[];
   apiService.GetSignals(signals);
   signalExecutor.Execute(signals);
  }
