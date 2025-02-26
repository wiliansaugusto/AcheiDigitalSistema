<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Usuario extends Model
{
    protected $table = 'usuarios';
    protected $primaryKey = 'id_usuario';
    public $timestamps = false;

    protected $fillable = [
        'nomeCompleto',
        'cpf',
        'email',
        'senha',
        'isAtivo',
        'dataNascimento',
    ];

    protected $casts = [
        'isAtivo' => 'boolean',
        'dataNascimento' => 'date',
        'dataCriacao' => 'datetime',
        'dataAlteracao' => 'datetime',
    ];

    public function contatos(): HasMany
    {
        return $this->hasMany(Contato::class, 'id_usuario');
    }

    public function enderecos(): HasMany
    {
        return $this->hasMany(Endereco::class, 'id_usuario');
    }
    public function tagHumano(): HasMany
    {
        return $this->hasMany(TagHumano::class, 'id_usuario');
    }
    protected static function boot()
    {
        parent::boot();

        static::creating(function ($model) {
            $model->dataCriacao = now();
            $model->dataAlteracao = now();
        });

        static::updating(function ($model) {
            $model->dataAlteracao = now();
        });
    }
}
