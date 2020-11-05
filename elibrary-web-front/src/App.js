import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {
    isLoading: true,
    userDataList: []
  };

  async componentDidMount() {
    const response = await fetch('/api/v1/user-data');
    const body = await response.json();
    this.setState({ userDataList: body, isLoading: false });
  }

  render() {
    const {userDataList, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
              <h2>eLibrary users</h2>
              {userDataList.map(userData =>
                  <div key={userData.id}>
                    {userData.login + " " + userData.email}
                  </div>
              )}
            </div>
          </header>
        </div>
    );
  }
}

export default App;