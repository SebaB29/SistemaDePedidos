import { useEffect, useState } from 'react'
import Header from '../components/Header'
import Loader from '../components/Loader'
import { Main } from '../components/Main'
import { OrderCard } from '../components/OrderCard'
import { helpHttp } from '../helpers/helpHttp'

export const Orders = () => {
  const [loading, setLoading] = useState(false)
  const [orders, setOrders] = useState([])
  let ENDPOINT = `http://localhost:8080/order?userId=${window.sessionStorage.getItem('user_id')}`
  if (window.sessionStorage.getItem('rol') === 'ADMIN') {
    ENDPOINT = 'http://localhost:8080/order'
  }

  useEffect(() => {
    setLoading(true)
    helpHttp().get(
      ENDPOINT,
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        setLoading(false)
        if (res.status === 'OK') setOrders(res.data.order_list)
      })
  }, [])

  return (
    <>
      <Header title='Ordenes' />
      <Main>
        <br />
        {loading && <Loader />}
        {orders && orders.map((order, index) => (
          <OrderCard
            key={index}
            order={order}
          />
        ))}
      </Main>
    </>
  )
}
