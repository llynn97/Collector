import React from 'react';
import TopBar from './components/TopBar/TopBar';
import MainContent from './components/MainContent/MainContent';
import {Route, Routes} from "react-router-dom";
import SignUp from './components/SignUp/SignUp';
import MainPage from './components/MainPage/MainPage';
import EventDetailPage from "./components/EventDetailPage/EventDetailPage";
import { Provider } from 'react-redux';
import store from './store';
import EventPage from './components/EventPage/EventPage';
import InformationSharePage from './components/InformationShares/InformationSharePage';
import InformationShareWritePage from './components/InformationShares/InformationShareWritePage';
import InformationShareDetailPage from './components/InformationShares/InformationShareDetailPage';
import TransactionPage from './components/TransactionPage/TransactionPage';
import DMPage from './components/DMPage/DMPage';

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <MainContent/>
      </Provider>
      
    </div>
  );
}

export default App;
