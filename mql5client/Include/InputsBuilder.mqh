#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class InputsBuilder
  {

   string            inputs;

   bool              is_builed;

   string            _input_string_formatter;

   string            _input_double_formatter;

   string            _input_integer_formatter;

public:
                     InputsBuilder()
     {
      inputs = "";
      is_builed = false;
      _input_string_formatter = "{ \"key\": %s, \"value\": %s }";
      _input_double_formatter = "{ \"key\": %s, \"value\": %G }";
      _input_integer_formatter = "{ \"key\": %s, \"value\": %d }";
     }
                    ~InputsBuilder() {}

public:
   void              InputString(string key, string value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() canbe called only once.");
         return;
        }
      string input_string = StringFormat(_input_string_formatter, key, value);
      inputs = StringFormat("%s,", input_string);
     }

   void              InputDouble(string key, double value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() canbe called only once.");
         return;
        }
      string input_double = StringFormat(_input_double_formatter, key, value);
      inputs = StringFormat("%s,", input_double);
     }

   void              InputInteger(string key, int value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() canbe called only once.");
         return;
        }
      string input_integer = StringFormat(_input_integer_formatter, key, value);
      inputs = StringFormat("%s,", input_integer);
     }

   void              InputTime(string key, datetime value)
     {
      InputInteger(key, (int) value);
     }

   string             Build()
     {
      if(inputs == "")
         return "{}";

      string json = StringSubstr(inputs, 0, StringLen(inputs) - 1);
      inputs = StringFormat("[%s]", json);
      is_builed = true;
      return inputs;
     }

   string             GetInputs()
     {
      if(is_builed)
        {
         Alert("Build() wasn't called. Method Build() should be called before GetInputs().");
         return "{}";
        }
      return inputs;
     }

  };
