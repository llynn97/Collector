import React, { useEffect, useState } from "react";
import axios from "axios";

const MainPosts = () => {
    const [dailyPosts, setDailyPosts] = useState([]);
    
    useEffect(() => {
        axios.get("http://localhost:8080/main/daily-community", {
            withCredentials: true,
            params: {
                community_category:"정보공유",
            }
        })
        .then(response => {
            console.log(response.data);
            setDailyPosts(response.data);
        })
        .catch(error => console.log(error))
    }, [])

    return (
        <>
        <div>
        {dailyPosts.map((item, index) => (
            <>
            {item.title}, {item.post_id}
            </>
        ))}
        </div>
        
        </>
    )
}

export default MainPosts;