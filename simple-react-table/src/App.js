// src/App.js
import React from "react";
import { useState, useEffect } from 'react';
import Table from "./Table";
import useWebSocket from 'react-use-websocket';

function App() {

    // Data Initialization

    const wsItemsURL = 'ws://localhost:8080/e-commerce-java/items'
    const { sendMessage, lastMessage, readyState } = useWebSocket(wsItemsURL);

    const [fullData, setFullData] = useState([]);
    const [data, setData] = useState([]);

    console.log("Full Data: ", fullData)
    console.log("Data: ", data)

    useEffect(() => {
        if(lastMessage !== null)
            readItems(lastMessage)
    }, [lastMessage]);

    // Get Items

    function readItems(lastMessage){
        let items = JSON.parse(lastMessage.data)
        saveDeepCopyToFullData(items)
        items = filterOutOfStockItems(items)
        items = addCurrentUnitsInStock(items)
        setData(items)
    }

    const saveDeepCopyToFullData = (items) => {
        setFullData(structuredClone(items))
        return items
    }

    const filterOutOfStockItems = (items) => {
        return items.filter(item => item["numUnitsInStock"]!=="0")
    }

    const addCurrentUnitsInStock = (items) => {
        return items.map(
            item => {
                    item["currentUnitsInStock"] = item["numUnitsInStock"]
                    return item
            })
    }

    // Post Items

    function postItems(){
        updateFullData()
        const message = JSON.stringify(fullData)
        sendMessage(message)
    }

    const updateFullData = () => {
        setFullData(
            fullData.map(row => updateFullDataRow(row))
        )
    }

    const updateFullDataRow = (row) => {
        const id = row["id"]
        const newUnitsInStock = data.filter(row => row["id"] === id)[0]["currentUnitsInStock"]
        row["numUnitsInStock"] = newUnitsInStock
        return row
    }

    // HTML to render

    return (
        <div style={{marginLeft: "1vw"}}>
            <h2>E-commerce</h2>
            <div>
                <Table data={data} setData={setData}/>
            </div>
            <div>
                <button onClick={postItems}>
                    Enviar
                </button>
            </div>
        </div>
    );
}

export default App;