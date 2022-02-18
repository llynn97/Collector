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
            onChange={e=>setUsername(e.target.value)}
          />
        </div>
        <div className={"contents"} style={{width:"200px", height:"300px"}}>
            
          {contents.map((message) => (
            <div> {message.username} : {message.content} </div>
          ))}
        </div>
        <div>
          <input
            placeholder="input your messages..."
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
          <button onClick={(value) => handleEnter(value)}>전송</button>
        </div>
      </div>
    );
  };

export default ChatPresenter;