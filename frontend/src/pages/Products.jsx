import { useState } from 'react'
import Header from '../components/Header'
import { Main } from '../components/Main'
import { ProductsForm } from '../components/ProductsForm'
import Loader from '../components/Loader'
import { ProductCard } from '../components/ProductCard'
import { useNavigate } from 'react-router-dom'

const initialProducts = [
  {
    id: 0,
    name: 'Pan',
    stock: 12,
    properties: [
      {
        id: 0,
        name: 'peso',
        description: '1kg'
      },
      {
        id: 1,
        name: 'harina',
        description: 'integral'
      }
    ]
  },
  {
    id: 1,
    name: 'agua',
    stock: 23,
    properties: [
      {
        id: 3,
        name: 'tipo',
        description: 'mineral'
      }
    ]
  }
]

export const Products = () => {
  const [loading, setLoading] = useState(false)
  const [products, setProducts] = useState(initialProducts)
  const navigate = useNavigate()
  // hacer fetch a productos
  // useEffect(() => {
  //   setLoading(true)
  //   helpHttp.get(
  //     'products endpoint',
  //     {
  //       headers: {
  //         'Content-Type': 'Application/json',
  //         Accept: 'application/json'
  //       }
  //     })
  //     .then(res => {
  //       setLoading(false)
  //       setProducts(res)
  //     })
  //   return () => {
  //   }
  // }, [])

  return (
    <>
      <Header title='Productos' />
      <Main>
        <ProductsForm setLoading={setLoading} setProducts={setProducts} />
        {window.sessionStorage.getItem('admin')
          ? <><button onClick={() => navigate('/create_product')}>Agregar Producto</button><hr /></>
          : <></>}
        {loading && <Loader />}
        {products.map((product) => (
          <ProductCard
            key={product.id}
            product={product}
          />
        ))}
      </Main>
    </>
  )
}
