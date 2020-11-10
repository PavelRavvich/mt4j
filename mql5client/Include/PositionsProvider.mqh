#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class PositionProvider
  {

   int               _magic;

   Position          _positionsCahe[];

   Position          _openCahe[];

   Position          _historyCahe[];

public:
                     PositionProvider(long magic) { _magic = magic; }
                    ~PositionProvider() { }


public:

   void              OnTickRefresh(datetime from)
     {

      // Fetch and Sort this.positions to this.open and this.closed
     }

   string            GetPositions()
     {
      // todo impl
      return "\"positions\": { \"open\": [], \"history\": [] }";
     }

};