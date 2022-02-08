import React from "react";
import { Link } from "react-router-dom";

const BottomCategory = () => {

    return(
        <>
        <div>
        <button>하위 카테고리</button>
        <Link to = {`/informationShare`}>
            <button>정보 공유</button>
        </Link>
        <button>하위 카테고리</button>
        <button>하위 카테고리</button>
        </div>
        </>
    );
}

export default BottomCategory;