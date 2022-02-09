import React from "react";
import {Link} from "react-router-dom";

const EventMovieThumbnail = ({event}) => {

    return(
        <>
        <Link to = {`/event/${event.event_id}`}>
        <h3>
            {event.thumbnail_url}
        </h3>
        <div>
            {event.title}, {event.start_date} ~ {event.end_date}, {event.cinema_name}
        </div>
        <div>
            {event.is_like ? <div>좋아요O</div> : <div>좋아요X</div>}
        </div>
        </Link>
        
        </>
    );
}

export default EventMovieThumbnail;