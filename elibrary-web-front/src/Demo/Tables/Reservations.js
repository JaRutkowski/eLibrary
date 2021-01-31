import React from 'react';
import { Row, Col, Card } from 'react-bootstrap';

import Aux from "../../hoc/_Aux";
import BootstrapBasicTable from './BootstrapBasicTable';

class Reservations extends React.Component {
    state = {
        isLoading: true,
        activeLends: []
    };

    async componentDidMount() {
        const response = await fetch('/api/v1/reservations/active-lends/1');
        const body = await response.json();
        this.setState({ activeLends: body, isLoading: false });
    }

    render() {
        const columns = [
            { Header: 'Title', accessor: 'title' },
            { Header: 'ISBN Number', accessor: 'isbnNumber' },
            { Header: 'Inventory Number', accessor: 'inventoryNumber' },
            { Header: 'Lend Date', accessor: 'lendDate' },
            { Header: 'Return Date', accessor: 'returnDate' }
        ];

        return (
            <Aux>
                <Row>
                    <Col>
                        <Card>
                            <Card.Header>
                                <Card.Title as="h5">Lends</Card.Title>
                                {/* <span className="d-block m-t-5">use bootstrap <code>Table</code> component</span> */}
                            </Card.Header>
                            <Card.Body>
                                <BootstrapBasicTable columns={columns} data={this.state.activeLends} />
                            </Card.Body>
                        </Card>
                        {/* <Card>
                            <Card.Header>
                                <Card.Title as="h5">Hover Table</Card.Title>
                                <span className="d-block m-t-5">use props <code>hover</code> with <code>Table</code> component</span>
                            </Card.Header>
                            <Card.Body>
                                <Table responsive hover>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Username</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th scope="row">1</th>
                                            <td>Mark</td>
                                            <td>Otto</td>
                                            <td>@mdo</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">2</th>
                                            <td>Jacob</td>
                                            <td>Thornton</td>
                                            <td>@fat</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">3</th>
                                            <td>Larry</td>
                                            <td>the Bird</td>
                                            <td>@twitter</td>
                                        </tr>
                                    </tbody>
                                </Table>
                            </Card.Body>
                        </Card> */}
                        {/* <Card>
                            <Card.Header>
                                <Card.Title as="h5">Striped Table</Card.Title>
                                <span className="d-block m-t-5">use props <code>striped</code> with <code>Table</code> component</span>
                            </Card.Header>
                            <Card.Body>
                                <Table striped responsive>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Username</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th scope="row">1</th>
                                            <td>Mark</td>
                                            <td>Otto</td>
                                            <td>@mdo</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">2</th>
                                            <td>Jacob</td>
                                            <td>Thornton</td>
                                            <td>@fat</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">3</th>
                                            <td>Larry</td>
                                            <td>the Bird</td>
                                            <td>@twitter</td>
                                        </tr>
                                    </tbody>
                                </Table>
                            </Card.Body>
                        </Card> */}
                    </Col>
                </Row>
            </Aux>
        );
    }
}

export default Reservations;