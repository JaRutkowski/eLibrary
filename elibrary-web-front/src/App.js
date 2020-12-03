import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import BoostratpBasicTable from "./Demo/Tables/ReservationsTable";

class App extends Component {
  state = {
    isLoading: true,
    activeLends: [],
    columns: []
  };

  async componentDidMount() {
    const response = await fetch('/api/v1/reservations/active-lends/9');
    const body = await response.json();
    const columns = await [
      {Header: 'Title', accessor: 'title'},
      {Header: 'ISBN Number', accessor: 'isbnNumber'},
      {Header: 'Inventory Number', accessor: 'inventoryNumber'},
      {Header: 'Lend Date', accessor: 'lendDate'},
      {Header: 'Return Date', accessor: 'returnDate'}
    ];
    this.setState({activeLends: body, isLoading: false, columns: columns});
  }

  render() {
    const {activeLends, isLoading, columns} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo"/>
            <div className="App-intro">
              <h2>Active Lends</h2>
              {/*{activeLends.map(userData =>*/}
                  {/*<div key={userData.id}>*/}
                    {/*{userData.login + " " + userData.email}*/}
                  {/*</div>*/}
              {/*)}*/}
              <BoostratpBasicTable columns={columns} data={activeLends}/>
            </div>
          </header>
        </div>
    );
  }
}

export default App;