#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class PositionProvider
  {

   int               _magic;

public:
                     PositionProvider(long magic) { _magic = magic; }
                    ~PositionProvider() { }


public:

   void              GetPositionById(long positionId, Position &position)
     {
      // todo
     }

   void              GetAllPositions(Position &positions[])
     {
      // todo
     }

   bool              IsPosition()
     {
      Position positions[];
      GetAllPositions(positions);
      return ArraySize(positions) > 0;
     }
};