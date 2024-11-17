import React from 'react'
import { helpHttp } from '../helpers/helpHttp'

const CREATE_ORDER_ENDPOINT = 'http://localhost:8080/order'

export const Carrito = ({ items }) => {
  const handleCreateOrder = () => {
    const productOrderList = items.map(item => {
      return { productId: Number(item.productId), quantity: parseFloat(item.quantity) }
    })
    helpHttp().post(
      CREATE_ORDER_ENDPOINT,
      {
        body: {
          userId: Number(window.sessionStorage.getItem('user_id')),
          productOrderDTOList: productOrderList
        },
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        window.alert(res)
        console.log(res)
      })
  }

  return (
    <div className='carrito'>
      <h2>Carrito</h2>
      <hr />
      {items.length === 0 && <p>carrito vacío</p>}
      <ul>
        {items.map((item, index) => (
          <li key={index}>{item.name} : {item.quantity}</li>
        ))}
      </ul>
      {items.length !== 0 && <button onClick={handleCreateOrder}>Realizar Pedido</button>}
    </div>
  )
}
