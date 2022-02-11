import React, {useState, useCallback} from "react";

const DMDetail = ({sendMessage}) => {
    const [ms, setMs] = useState("");
    const onChange = useCallback(
        (e) => {
            setMs(e.target.value);
        }, []
    )

    const onClick = () => {
        sendMessage(ms);
        setMs("");
    }
    return (
        <>
        <input type="text" value={ms} onChange={onChange} name={ms}/>
        <button onClick={onClick}>전송</button>
        </>
    );
}

export default DMDetail;