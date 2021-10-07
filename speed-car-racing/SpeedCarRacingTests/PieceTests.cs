﻿using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SpeedCarRacing.Tests
{
    [TestClass()]
    public class PieceTests
    {
        [TestMethod()]
        public void IsBrokenTest()
        {
            Piece piece = new Piece(Piece.PieceName.MOTOR);
            Assert.IsFalse(piece.IsBroken());
            piece.Break();
            Assert.IsTrue(piece.IsBroken());
        }

        [TestMethod()]
        public void BreakTest()
        {
            Piece piece = new Piece(Piece.PieceName.MOTOR);
            Assert.IsFalse(piece.IsBroken());
            piece.Break();
            Assert.IsTrue(piece.IsBroken());
        }

        [TestMethod()]
        public void RepareTest()
        {
            Piece piece = new Piece(Piece.PieceName.MOTOR);
            piece.Break();
            Assert.IsTrue(piece.IsBroken());
            piece.Repare();
            Assert.IsFalse(piece.IsBroken());
        }
    }
}