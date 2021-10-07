﻿using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;

 namespace SpeedCarRacing.Tests
{
    [TestClass()]
    public class FastCarTests
    {
        private FastCar car;

        [TestInitialize()]
        public void Initialize() {
            car = new FastCar(AbstractCar.CarColor.BLUE);
        }

        [TestMethod()]
        [Ignore]
        public void SpecialTest()
        {
            Assert.Fail();
        }

        [TestMethod()]
        public void InitPiecesTest()
        {
            int countAllPieces = Enum.GetNames(typeof(Piece.PieceName)).Length;
            Assert.AreEqual(countAllPieces, car.Pieces.Count);
        }

        [TestMethod()]
        public void RandomBreakPieceTest()
        {
            Assert.AreEqual(AbstractCar.CarStatus.HEALTHY, car.Status);
            Assert.AreEqual(true, CheckHealthyPieces(car.Pieces));

            car.RandomBreakPiece();

            Assert.AreEqual(AbstractCar.CarStatus.BROKEN, car.Status);
            Assert.AreEqual(false, CheckHealthyPieces(car.Pieces));
        }

        private bool CheckHealthyPieces(List<Piece> pieces)
        {
            return pieces.TrueForAll(piece => piece.Status == Piece.PieceStatus.HEALTHY);
        }

        [TestMethod()]
        public void BreakPieceTest()
        {
            Piece piece = car.Pieces[0];
            Assert.AreEqual(false, piece.IsBroken());
            Assert.AreEqual(AbstractCar.CarStatus.HEALTHY, car.Status);
            car.BreakPiece(piece);
            Assert.AreEqual(true, piece.IsBroken());
            Assert.AreEqual(AbstractCar.CarStatus.BROKEN, car.Status);
        }

        [TestMethod()]
        public void ReparePieceTest()
        {
            Piece piece = car.Pieces[0];
            car.BreakPiece(piece);
            Assert.AreEqual(true, piece.IsBroken());
            Assert.AreEqual(AbstractCar.CarStatus.BROKEN, car.Status);
            car.ReparePiece(piece);
            Assert.AreEqual(false, piece.IsBroken());
            Assert.AreEqual(AbstractCar.CarStatus.HEALTHY, car.Status);
        }
    }
}