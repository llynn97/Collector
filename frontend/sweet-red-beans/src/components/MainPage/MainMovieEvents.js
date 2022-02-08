import React, { useEffect, useState } from "react";
import MovieThumbnail from "./MovieThumbnail";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import { useDispatch, useSelector } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";

const MainMovieEvents = ({cinemaName}) => {
    
    return (
        <>
        <h3>{cinemaName}</h3>
        <MovieThumbnail cinemaName={cinemaName}/>
        </>
        
    );
}

export default MainMovieEvents;