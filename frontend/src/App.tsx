import { Button } from '@/components/ui/button'

function App() {
  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-4">
      <h1 className="text-2xl font-semibold">Ledger2</h1>
      <p className="text-muted-foreground">Front-end configurado (Tailwind + shadcn/ui)</p>
      <Button>Teste</Button>
    </div>
  )
}

export default App
