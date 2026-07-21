"use client"

import { useState } from "react"
import { ChevronDown } from "lucide-react"

const faqs = [
  {
    question: "O bypass é seguro de usar?",
    answer:
      "Sim. Nosso sistema é atualizado diariamente e utiliza técnicas indetectáveis, mantidas por uma equipe dedicada para garantir estabilidade e segurança.",
  },
  {
    question: "Posso gerenciar várias contas ao mesmo tempo?",
    answer:
      "Com certeza. O painel foi feito para gerenciar múltiplas contas simultaneamente, com troca instantânea e organização por tags.",
  },
  {
    question: "Preciso instalar algum programa?",
    answer:
      "O painel é totalmente baseado na web. Você acessa de qualquer lugar, sem instalações complexas.",
  },
  {
    question: "Posso cancelar quando quiser?",
    answer:
      "Sim. Não há contratos de fidelidade. Você pode cancelar sua assinatura a qualquer momento direto pelo painel.",
  },
  {
    question: "Existe suporte disponível?",
    answer:
      "Oferecemos suporte por e-mail em todos os planos e suporte prioritário 24/7 nos planos Pro e Enterprise.",
  },
]

export function Faq() {
  const [open, setOpen] = useState<number | null>(0)

  return (
    <section id="faq" className="relative px-4 py-24">
      <div className="mx-auto max-w-3xl">
        <div className="text-center">
          <span className="text-sm font-medium text-primary">FAQ</span>
          <h2 className="mt-2 text-balance text-3xl font-bold tracking-tight md:text-4xl">
            Perguntas frequentes
          </h2>
        </div>

        <div className="mt-12 flex flex-col gap-3">
          {faqs.map((faq, index) => (
            <div
              key={faq.question}
              className="rounded-2xl border border-border bg-card/50 backdrop-blur-sm"
            >
              <button
                type="button"
                onClick={() => setOpen(open === index ? null : index)}
                className="flex w-full items-center justify-between gap-4 px-6 py-5 text-left"
                aria-expanded={open === index}
              >
                <span className="font-medium">{faq.question}</span>
                <ChevronDown
                  className={`h-5 w-5 shrink-0 text-muted-foreground transition-transform ${
                    open === index ? "rotate-180" : ""
                  }`}
                />
              </button>
              {open === index && (
                <p className="px-6 pb-5 text-sm leading-relaxed text-muted-foreground">
                  {faq.answer}
                </p>
              )}
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
