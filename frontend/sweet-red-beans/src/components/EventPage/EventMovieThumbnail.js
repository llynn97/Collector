import React from "react";
import {Link} from "react-router-dom";

const EventMovieThumbnail = ({event}) => {

    return(
        <>
        <Link to = {`/event/${event.event_id}`}>
        <h3>
            <img src={event.thumbnail_url} width="300px" height="200px"/>
        </h3>
        <div>
            {event.title}, {event.start_date} ~ {event.end_date}, {event.cinema_name}
        </div>
        <div>
            {event.is_like ? <button>좋아요O</button> : <button>좋아요X</button>}
        </div>
        </Link>
        
        </>
    );
}

export default EventMovieThumbnail;