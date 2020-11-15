#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Enums.mqh>
#include <Structures.mqh>


string PositionTypeToString(PositionType type)
  {
   switch(type)
     {
      case LONG:
         return "BUY";
      case SHORT:
         return "SELL";
     }
   Alert("Unexpected enum value PositionType can't be: ", type, "Expert was stopped!");
   ExpertRemove();
   return "ERROR";
  }

string BoolToString(bool val)
  {
   return val ? "true" : "false";
  }