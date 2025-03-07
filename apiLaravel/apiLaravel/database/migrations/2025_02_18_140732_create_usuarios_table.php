<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->bigIncrements('id_usuario');
            $table->string('nomeCompleto', 100);
            $table->string('cpf', 11)->nullable();
            $table->string('email');
            $table->string('senha');
            $table->boolean('isAtivo')->default(true);
            $table->date('dataNascimento')->nullable();
            $table->timestamp('dataCriacao')->useCurrent();
            $table->timestamp('dataAlteracao')->nullable();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('usuarios');
    }
};
