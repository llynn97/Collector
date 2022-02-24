import React from "react";

const ChatPresenter = ({
  contents,
  message,
  username,
  setMessage,
  setUsername,
  handleEnter,
}) => {
  return (
    <div className={"chat-box"}>
      <div className='header'>
        유저이름 : 
        <input
          style={{flex : 1}}
          value={username}
          onChange={e=>{setUsername(e.target.value)
          console.log(e.target.value);}}
        />
      </div>
      <div className={"contents"}  style={{width:"100px", height:"300px"}}>
        {contents.map((message, index) => (
          <div key={index}> {message.username} : {message.content} </div>
        ))}
      </div>
      <div>
        <input
          type="text"
          placeholder="input your messages..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button onClick={(message) => handleEnter(username,message)}>
          Enter
        </button>
      </div>
    </div>
  );
};

export default ChatPresenter;