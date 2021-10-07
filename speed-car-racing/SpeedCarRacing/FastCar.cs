using System;
using System.Collections.Generic;

namespace SpeedCarRacing
{
    public class FastCar : AbstractCar
    {
        public List<Piece> Pieces { get; }

        public FastCar(CarColor color) : base(color)
        {
            Pieces = new List<Piece>();
            Speed = Config.FAST_CAR_SPEED;
            SpecialProb = Config.FAST_CAR_SPECIAL_PROB;
            InitPieces();
        }

        public void InitPieces()
        {
            foreach (Piece.PieceName piece in Enum.GetValues(typeof(Piece.PieceName)))
            {
                Pieces.Add(new Piece(piece));
            }
        }

        public void RandomBreakPiece() 
        {
            Random random = new Random();
            int randIndex = random.Next(Pieces.Count);
            Piece piece = Pieces[randIndex];
            BreakPiece(piece);
        }

        public void BreakPiece(Piece piece)
        {
            piece.Break();
            Status = CarStatus.BROKEN;
        }

        public void ReparePiece(Piece piece)
        {
            piece.Repare();
            Status = CarStatus.HEALTHY;
        }

        protected override void BeforeSpecial()
        {
            if (Status == CarStatus.BROKEN)
            {
                Piece piece = GetBrokenPiece();
                string pieceName = piece.Name.ToString();
                if (SpecialUserInput(pieceName))
                {
                    ReparePiece(piece);
                    Speed = Config.FAST_CAR_SPEED;;
                }
            }
        }

        private Piece GetBrokenPiece()
        {
            Piece brokenPiece = null;
            Pieces.ForEach(piece =>
            {
                if (piece.IsBroken())
                {
                    brokenPiece = piece;
                }
            });
            return brokenPiece;
        }
        
        private bool SpecialUserInput(string word)
        {
            Console.WriteLine("# Enter '{0}' to repare your broken piece:", word);
            string userInput = Console.ReadLine();
            if (userInput != null && userInput.ToUpper() == word)
            {
                Console.WriteLine("# Piece repaired :)");
                return true;
            }
            Console.WriteLine("# You failed :(");
            return false;
        }

        public override void Special()
        {
            RandomBreakPiece();
            Speed = 0;
        }

        public override void UpdateCycle() { }

    }
}