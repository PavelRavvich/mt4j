#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Enums.mqh>
#include <Structures.mqh>

string PositionTypeToString(PositionType type)
  {
   switch(type)
     {
      case BUY:
         return "BUY";
      case SELL:
         return "SELL";
      case NO_ACTION:
         return "NO_ACTION";
     }
   return "";
  }

string BoolToString(bool val)
  {
   return val ? "true" : "false";
  }