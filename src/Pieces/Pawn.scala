package Pieces

import Game.Helpers.Color.Color
import Game.Helpers.Type.Type
import Game._
import Helpers.{Color, Type}

class Pawn(color: Color) extends Piece(color: Color) {

  def pieceType: Type = Type.Pawn

  override
  def isValidMoveSet(state: Array[Array[Spot]], move: Move): Boolean = {

    val from = move.from
    val to = move.to

    if (!super.isValidMoveSet(state, move)) return false

    var valid = false

    val legalPairs  = if (color == Color.White) Array( (1,0),(2,0),(1,1),(1,-1))
    else Array( (-1,0),(-2,0),(-1,1),(-1,-1) )

    for (p <- legalPairs ) {
      if(to._1 == from._1 + p._1 && to._2 == from._2 + p._2){
        if(to._2 != from._2){
          if(state(to._1)(to._2).isOccupied){
            if(state(to._1)(to._2).piece.color != this.color){
              valid = true
            }
          }
        }
        else{
          if(Math.abs(to._1 - from._1) == 2){
            if(!this.moved){
              valid = true
            }
          }
          else{
            valid = true
          }
        }
      }
    }

    valid
  }


  def blockedByPiece(state: Array[Array[Spot]], move: Move): Boolean = {
    val to = move.to
    val from = move.from

    if (to._2 != from._2) return false

    if(to._1 > from._1){
      for(i <- from._1 + 1 to to._1 by 1){
        if(state(i)(from._2).isOccupied) return true
      }
    }

    else {
      for (i <- from._1 - 1 to to._1 by -1) {
        if (state(i)(from._2).isOccupied) return true
      }
    }

    false
  }

}
