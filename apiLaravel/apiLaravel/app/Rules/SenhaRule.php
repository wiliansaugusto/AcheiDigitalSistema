<?php

namespace App\Rules;

use Closure;
use Illuminate\Contracts\Validation\Rule as ValidationRule;

class SenhaRule implements ValidationRule
{
    public function passes($attribute, $value)
    {
        // Verifica se é nulo (equivalente ao @NotNull)
        if ($value === null) {
            return false;
        }

        // Verifica o tamanho mínimo (equivalente ao @Size(min=8))
        if (strlen($value) < 8) {
            return false;
        }

        // Verifica o padrão de segurança (equivalente ao @Pattern)
        $regex = '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*()!])[A-Za-z\d@#$%^&*()!]{8,}$/';

        if (!preg_match($regex, $value)) {
            return false;
        }

        return true;
    }

    public function message()
    {
        return 'A senha está com problemas de segurança.';
    }

    public function validate(string $attribute, mixed $value, Closure $fail): void
    {
        // Verifica se é nulo (equivalente ao @NotNull)
        if ($value === null) {
            $fail('O campo senha não pode ser nulo.');
            return;
        }

        // Verifica o tamanho mínimo (equivalente ao @Size(min=8))
        if (strlen($value) < 8) {
            $fail('A senha deve conter no mínimo 8 caracteres.');
            return;
        }

        // Verifica o padrão de segurança (equivalente ao @Pattern)
        $regex = '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*()!])[A-Za-z\d@#$%^&*()!]{8,}$/';

        if (!preg_match($regex, $value)) {
            $fail('A senha está com problemas de segurança.');
        }
    }
}
