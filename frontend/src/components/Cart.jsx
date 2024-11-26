import React from 'react'
import { helpHttp } from '../helpers/helpHttp'

const CREATE_ORDER_ENDPOINT = 'http://localhost:8080/order'

export const Cart = ({ thisCart, cart, setCart, myOnClick, carts, setCarts }) => {
  const calssName = cart[0] === thisCart[0] ? 'cart cart-selected' : 'cart'

  const handleCreateOrder = () => {
    const productOrderList = thisCart.slice(1).map(item => {
      return { product_id: Number(item.productId), quantity: parseFloat(item.quantity) }
    })
    helpHttp().post(
      CREATE_ORDER_ENDPOINT,
      {
        body: {
          orderState: 0,
          orderId: 1,
          user_id: Number(window.sessionStorage.getItem('user_id')),
          products: productOrderList
        },
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        if (res.status === 'CREATED') {
          window.alert(res.data.message)
          handleDeleteOrder()
        } else {
          window.alert(res.error)
        }
      })
  }

  const handleDivideOrder = () => {
    const newCarts = [...carts]
    const order1 = [thisCart[0]]
    const order2 = [carts.at(-1)[0] + 1]
    thisCart.slice(1).forEach(product => {
      const particion1 = Math.ceil(Number(product.quantity) / 2)
      const particion2 = product.quantity - particion1
      order1.push({ name: product.name, productId: product.productId, quantity: particion1.toString() })
      if (particion2 !== 0) {
        order2.push({ name: product.name, productId: product.productId, quantity: particion2.toString() })
      }
    })
    if (order2.length >= 2) {
      newCarts.splice(thisCart[0], 1, order1)
      newCarts.push(order2)
      setCarts(newCarts)
    } else {
      window.alert('El pedidio no se puede dividir más')
    }
  }

  const handleDeleteOrder = () => {
    const newCarts = carts.filter(el => el[0] !== thisCart[0])
    if (newCarts.length === 0) {
      setCarts([[0]])
      setCart([0])
    } else {
      setCarts(newCarts)
      if (cart[0] === thisCart[0]) setCart(newCarts[0])
    }
  }

  return (
    <div className={calssName} onClick={e => myOnClick(e, thisCart)}>
      <button className='cart-button delete-order-button' onClick={handleDeleteOrder}>X</button>
      <h2>Carrito {thisCart[0]}</h2>
      <hr />
      {thisCart.length === 1 && <p>carrito vacío</p>}
      <ul>
        {thisCart.slice(1).map((item, index) => (
          <li key={index}>{item.name} : {item.quantity}</li>
        ))}
      </ul>
      {thisCart.length >= 2 && <button className='cart-button' onClick={handleCreateOrder}>Realizar Pedido</button>}
      <br />
      <br />
      {thisCart.length >= 2 && <button className='cart-button' onClick={handleDivideOrder}>Dividir Pedido</button>}
    </div>
  )
}
