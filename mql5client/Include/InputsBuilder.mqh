#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

/*
   InputsBuilder usage example.

   InputsBuilder * ib = new InputsBuilder();
   ib.InputString("input_name_1", "input_value_1");
   ib.InputDouble("input_name_2", 1.1234);
   ib.InputInteger("input_name_3", 12345);
   ib.InputLong("input_name_4", 12345678);
   datetime now = TimeCurrent();
   ib.InputDatetime("input_name_5", now);
   ib.InputTimeSeconds("input_name_6", (int) now);
   ib.InputTimeMilliseconds("input_name_7", now * 1000);
   string jsonBuild = ib.Build(); // InputsBuilder instance stay imutable.
   string jsonGet = ib.GetInputs(); // Can be used only after .Build()
*/

// Build JSON array from inputs.
class InputsBuilder
  {

   string            inputs;

   bool              is_builed;

   string            _empty_inputs;

   string            _input_datetime_formatter;

   string            _input_string_formatter;

   string            _input_double_formatter;

   string            _input_long_formatter;

   string            _keys[];

public:
                     InputsBuilder()
     {
      inputs = "";
      is_builed = false;
      _empty_inputs = "[]";
      _input_long_formatter = "{ \"key\": \"%s\", \"value\": %.0f, \"type\": \"NUMBER\" }";
      _input_datetime_formatter = "{ \"key\": \"%s\", \"value\": \"%s\", \"type\": \"DATETIME\" }";
      _input_string_formatter = "{ \"key\": \"%s\", \"value\": \"%s\", \"type\": \"STRING\" }";
      _input_double_formatter = "{ \"key\": \"%s\", \"value\": %.7f, \"type\": \"NUMBER\" }";
     }
                    ~InputsBuilder() {}

public:

   string             Build()
     {
      if(inputs == "")
        {
         Alert("Warning! Inputs is empty.");
         return _empty_inputs;
        }

      is_builed = true;
      return inputs = "[" + StringSubstr(inputs, 0, StringLen(inputs) - 2) + "]";
     }

   string             GetInputs()
     {
      if(!is_builed)
        {
         Alert("Build() wasn't called. Method Build() should be called before GetInputs().");
         return _empty_inputs;
        }
      return inputs;
     }

   void              InputString(string key, string value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() can be called only once.");
         return;
        }

      if(ContainKey(key))
        {
         Alert(StringFormat("Error add input property! Key: %s already exist.", key));
         return;
        }

      AddKey(key);
      inputs = inputs + StringFormat(_input_string_formatter, key, value) + ", ";
     }

   void              InputDouble(string key, double value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() can be called only once.");
         return;
        }

      if(ContainKey(key))
        {
         Alert(StringFormat("Error add input property! Key: %s already exist.", key));
         return;
        }

      AddKey(key);
      inputs = inputs + StringFormat(_input_double_formatter, key, value) + ", ";
     }

   void              InputLong(string key, long value)
     {
      if(is_builed)
        {
         Alert("Build() already done. Method Build() can be called only once.");
         return;
        }

      if(ContainKey(key))
        {
         Alert(StringFormat("Error add input property! Key: %s already exist.", key));
         return;
        }

      AddKey(key);
      inputs = inputs + StringFormat(_input_long_formatter, key, value) + ", ";
     }

   void              InputInteger(string key, int value)
     {
      InputLong(key, (long) value);
     }

   void              InputDatetime(string key, datetime date)
     {
      InputLong(key, (long) date * 1000);
     }

   void              InputTimeSeconds(string key, int seconds)
     {
      InputLong(key, (long) seconds * 1000);
     }

   void              InputTimeMilliseconds(string key, long milliseconds)
     {
      InputLong(key, milliseconds);
     }

   string            ToString()
     {
      return inputs;
     }

private:

   void              AddKey(string key)
     {
      int size = ArraySize(_keys);
      ArrayResize(_keys, size + 1);
      _keys[size] = key;
     }

   bool              ContainKey(string key)
     {
      for(int i = 0; i < ArraySize(_keys); i++)
         if(_keys[i] == key)
            return true;

      return false;
     }

  };