namespace SpeedCarRacing
{
    public class Piece
    {
        public enum PieceName { MOTOR, CAR_WHEELS_FR, CAR_WHEELS_FL, CAR_WHEELS_BR, CAR_WHEELS_BL, STEERING_WHEEL, BRAKES }
        public enum PieceStatus { HEALTHY, BROKEN }

        public PieceStatus Status { get; set; }

        public PieceName Name { get; }

        public Piece(PieceName name)
        {
            Name = name;
            Status = PieceStatus.HEALTHY;
        }

        public bool IsBroken()
        {
            return Status == PieceStatus.BROKEN;
        }

        public void Break()
        {
            Status = PieceStatus.BROKEN;
        }

        public void Repare()
        {
            Status = PieceStatus.HEALTHY;
        }

        public override string ToString()
        {
            return Name + " [" + Status + "]";
        }

    }
}
