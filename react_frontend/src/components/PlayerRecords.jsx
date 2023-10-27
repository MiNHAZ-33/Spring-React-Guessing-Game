import React, { useEffect, useState } from 'react';
import { Card } from 'react-bootstrap';
import axios from 'axios';
import { Link } from 'react-router-dom';

const PlayerRecords = () => {

    const [records, setRecords] = useState({
        gamesWon: 0,
        gamesLost: 0
    });
    const [error, setError] = useState(false);

    let userName = localStorage.getItem("userName");
    useEffect(() => {
        axios.get(`http://localhost:8080/api/profiles?userName=${userName}`)
            .then(res => {
                console.log(res.data)
                setRecords({
                    gamesWon: res.data.gameWon,
                    gamesLost: res.data.gameLost
                })
            }).catch(err => {
                console.log(err)
                setError(true)
            })
    }, [records.gamesWon, records.gamesLost, error])

    return (

        < div className='container' >
            <Card>
                <Card.Header>
                    <h1 className='text-center'>Playing record of {userName}</h1>
                </Card.Header>
                <Card.Body>
                    <div className="row">
                        <div className="col-md-6">
                            <div className="d-flex flex-column align-items-center">
                                <h3>Number of games won</h3>
                                <h1>{records.gamesWon}</h1>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="d-flex flex-column align-items-center">
                                <h3>Number of games lost</h3>
                                <h1>{records.gamesLost}</h1>
                            </div>
                        </div>

                    </div>
                </Card.Body>
                <Card.Footer>
                    <Link to={'/'}>
                        <div className='text-center'>
                            <  button className="btn btn-primary" >Back to homepage</button>
                        </div>
                    </Link>
                </Card.Footer>

            </Card>
        </div>
    )
}

export default PlayerRecords