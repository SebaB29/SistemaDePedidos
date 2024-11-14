import { useEffect, useState } from 'react'
import Header from '../components/Header'
import { Main } from '../components/Main'
import { ProductsForm } from '../components/ProductsForm'
import Loader from '../components/Loader'
import { ProductCard } from '../components/ProductCard'
import { useNavigate } from 'react-router-dom'
import { helpHttp } from '../helpers/helpHttp'
import Message from '../components/Message'

const ENDPOINT = 'http://localhost:8080/product'

export const Products = () => {
  const [loading, setLoading] = useState(false)
  const [response, setResponse] = useState({})
  const navigate = useNavigate()

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
        setResponse(res)
      })
      .catch(err => {
        setLoading(false)
        setResponse({
          status: '500',
          error: err,
          data: null
        })
      })
  }, [])

  return (
    <>
      <Header title='Productos' />
      <Main>
        <ProductsForm setLoading={setLoading} setProducts={setResponse} />
        {window.sessionStorage.getItem('admin')
          ? <><button onClick={() => navigate('/create_product')}>Agregar Producto</button><hr /></>
          : <></>}
        {loading && <Loader />}
        {response.status
          ? response.status === 'OK'
            ? response.data.map((product, index) => (
              <ProductCard
                key={index}
                product={product}
              />
            ))
            : <Message bgColor='#ff0000' message={response.status + ': ' + response.message} />
          : <></>}
      </Main>
    </>
  )
}
