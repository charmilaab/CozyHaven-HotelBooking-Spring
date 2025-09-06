export default function RoomPicker({ rooms = [], onPick }) {
  if (!rooms.length) return <div>No rooms available.</div>;
  return (
    <div style={{display:"grid", gap:8}}>
      {rooms.map(r => (
        <div key={r.roomId} style={{border:"1px solid #eee", padding:10, borderRadius:8}}>
          <div><b>Type:</b> {r.roomType}</div>
          <div><b>baseFare:</b> {r.baseFare}</div>
          <div><b>Capacity:</b> {r.maxOccupancy}</div>
          <button onClick={() => onPick(r.roomId)}>Book this room</button>
        </div>
      ))}
    </div>
  );
}
