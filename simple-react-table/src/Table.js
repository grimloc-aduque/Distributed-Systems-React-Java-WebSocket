// src/Table.js
import React from "react";
import { useState, useMemo } from 'react';
import { useTable } from "react-table";

const currencyToNumber = (currency) => {
    return Number(currency.replace(/[^0-9.-]+/g,""))
}

function Table({data, setData}) {

    // Total Order Amount

    const [total, setTotal] = useState(0);

    const showTotalOrderAmount = () => {
        document.getElementById("total").style.display = "block"
    }

    // Custom cell for unitsToPurchase

    const EditableCellUnitsToPurchase = ({value: initialValue, row: {index}}) => {
        const [unitsToPurchase, setUnitsToPurchase] = useState(initialValue);
        const onChange = e => {
            if(e.target.value >= 0)
                setUnitsToPurchase(e.target.value)
        };
        const onBlur = e => {
            if(e.target.value === ""){
                setUnitsToPurchase(0)
                unitsToPurchaseUpdated(index, 0, setUnitsToPurchase);
            }else{
                unitsToPurchaseUpdated(index, unitsToPurchase, setUnitsToPurchase);
            }
        }
        return <input value={unitsToPurchase} onChange={onChange} onBlur={onBlur} style={{width: "95%", textAlign: "center"}}/>
    }

    const unitsToPurchaseUpdated = (rowIndex, newUnitsToPurchase, setUnitsToPurchase) => {
        newUnitsToPurchase = parseInt(newUnitsToPurchase)
        setData(data =>{
            // Update Row in Data
            const newData = data.map(
                (row, index) => {
                    if (index === rowIndex) {
                        const newUnitsInStock = Number(row["numUnitsInStock"]) - newUnitsToPurchase
                        if(newUnitsInStock<0){
                            alert("The units to purchase cant exceed the units in stock")
                            setUnitsToPurchase(0)
                            unitsToPurchaseUpdated(index, 0, setUnitsToPurchase);
                        }else{
                            row["numUnitsToPurchase"] = newUnitsToPurchase
                            row["totalItemPrice"] = "$" + (newUnitsToPurchase * currencyToNumber(row["pricePerUnit"])).toFixed(2)
                            row["currentUnitsInStock"] = newUnitsInStock
                        }
                    }
                    return row;
                }
            )
            // Update total
            setTotal(
                newData
                    .map(row => currencyToNumber(row["totalItemPrice"]))
                    .reduce((sum, a) => sum + a, 0)
                    .toFixed(2)
            );
            return newData;
        });
    }

    // React Table

    const columns = useMemo(
        () => [
            {
                Header: "id",
                accessor: "id"
            },
            {
                Header: "Number of Units to Purchase",
                accessor: "numUnitsToPurchase",
                Cell: EditableCellUnitsToPurchase
            },
            {
                Header: "Total Item Price",
                accessor: "totalItemPrice"
            },
            {
                Header: "Serial Number",
                accessor: "serialNumber"
            },
            {
                Header: "Product Name",
                accessor: "productName"
            },
            {
                Header: "Price per Unit",
                accessor: "pricePerUnit"
            },
            {
                Header: "Number of Units Currently In Stock",
                accessor: "currentUnitsInStock"
            },
        ],
        []
    );

    const cellStyle = {
        textAlign: "center",
        paddingLeft: "1vw",
        paddingRight: "1vw"
    }

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = useTable({columns, data});

    // HTML to render

    return (
        <>
            <table {...getTableProps()} border="1">
                <thead>
                {headerGroups.map((headerGroup) => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map((column) => (
                            <th {...column.getHeaderProps()} style={cellStyle}>{column.render("Header")}</th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                    prepareRow(row);
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map((cell) => {
                                return <td {...cell.getCellProps()} style={cellStyle} >{cell.render("Cell")}</td>;
                            })}
                        </tr>
                    );
                })}
                </tbody>
            </table>
            <div style={{"margin-top": "1vw", "margin-bottom": "1vw"}}>
                <button onClick={showTotalOrderAmount}>Total Order Amount</button>
                <p id="total" style={{"display":"none"}}>Total: ${total}</p>
            </div>
        </>
    );
}

export default Table;