import Countdown from "react-countdown";

const CountdownTimer = () => {
    const targetDate =
        Date.now() +
        147 * 24 * 60 * 60 * 1000 +
        21 * 60 * 60 * 1000 +
        7 * 60 * 1000;

    const renderer = ({ days, hours, minutes, seconds }) => {
        return (
            <div className="bg-red-700 text-white px-6 py-3 rounded-2xl flex items-center justify-center space-x-4 text-center">
                <TimeBlock value={days} label="ngày" />
                <TimeBlock value={hours} label="giờ" />
                <TimeBlock value={minutes} label="phút" />
                <TimeBlock value={seconds} label="giây" />
            </div>
        );
    };

    return <Countdown date={targetDate} renderer={renderer} />;
};

const TimeBlock = ({ value, label }) => (
    <div>
        <div className="text-3xl font-bold">
            {value.toString().padStart(2, "0")}
        </div>
        <div className="text-sm">{label}</div>
    </div>
);

export default CountdownTimer;
